package com.example.house_kt_demo.service

import com.example.house_kt_demo.dto.HousingDto
import com.example.house_kt_demo.entity.Housing
import com.example.house_kt_demo.exception.UserNotExistException
import com.example.house_kt_demo.repo.CustomerRepo
import com.example.house_kt_demo.repo.HousingRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Optional
import java.util.stream.Collectors

@Service
class HousingService(@Autowired val housingRepo: HousingRepo, @Autowired val customerService: CustomerService,
        @Autowired val customerRepo: CustomerRepo) {

    fun findAll(page:Int, pageSize:Int): List<HousingDto> {
        var pageable = PageRequest.of(page,pageSize)
        var list = housingRepo.findAll(pageable)
        return list.stream().map { toDto(it)}.collect(Collectors.toList())
    }

    fun createHousing(email:String, housingDto: HousingDto): HousingDto {
        var customer = customerRepo.findCustomerByEmail(email)
        var entity = toEntity(housingDto)
        customer.addHousing(entity)
        housingRepo.save(entity)
        housingDto.customerDto = customerService.toDto(customer)
        return housingDto
    }

    fun dynamicSearch(page: Int ,size:Int ,housingName: Optional<String>, numberOfFloors: Optional<Int>, amount: Optional<Int>): List<HousingDto> {

        var pageable = PageRequest.of(page, size)
        var list = housingRepo.findAll(whichName(housingName).and(whichFloors(numberOfFloors)
                .and(whichAmount(amount))), pageable)

        return list.stream().map { toDto(it) }.collect(Collectors.toList())
    }

    fun whichName(name:Optional<String>):Specification<Housing>{

        if (name.isEmpty)
            return Specification.where(null)
        else
           return Specification { root, query, cb ->
                cb.like(cb.lower(root.get("housingName")),name.get().toLowerCase().plus("%") )
            }
    }

    fun whichFloors(floors:Optional<Int>):Specification<Housing>{
        if (!floors.isPresent)
            return Specification.where(null)
        else
            return Specification { root, query, cb ->
                cb.equal(root.get<Int>("numberOfFloors"), floors.get())
            }
    }

    fun whichAmount(amount:Optional<Int>):Specification<Housing>{
        if (!amount.isPresent)
            return Specification.where(null)
        else
            return Specification { root, query, cb ->
                cb.equal(root.get<Int>("amount"), amount.get())
            }
    }

    fun toEntity(dto:HousingDto):Housing{
        return Housing(dto.housingName, dto.address,dto.numberOfFloors,dto.amount, LocalDate.parse(dto.createDate), LocalDate.parse(dto.updatedDate))
    }

    fun toDto(entity:Housing):HousingDto{
        var dto =  HousingDto(entity.housingName,
                entity.address, entity.numberOfFloors, entity.amount,
                entity.createDate.toString(), entity.updatedDate.toString())
        dto.customerDto = customerService.toDto(entity.customer)
        return dto
    }

    fun edit(id: Int, dto: HousingDto): HousingDto {

        var auth = SecurityContextHolder.getContext().authentication
        var housingList = customerRepo.findCustomerByEmail(auth.name).housingList
        var existingHousing = housingRepo.findById(id).orElseThrow{ UserNotExistException() }

        if(housingList.contains(existingHousing)){
            existingHousing.housingName = dto.housingName
            existingHousing.address = dto.address
            existingHousing.amount = dto.amount
            existingHousing.numberOfFloors = dto.numberOfFloors
            existingHousing.createDate = LocalDate.parse(dto.createDate)
            existingHousing.updatedDate = LocalDate.parse(dto.updatedDate)
            housingRepo.save(existingHousing)
            dto.customerDto = customerService.toDto(existingHousing.customer)
            return dto;
        }
       else
           throw UserNotExistException()
    }

    fun helloHousing(name: String): List<HousingDto> {
        var customer = customerRepo.findCustomerByEmail(name)

        return customer.housingList.stream().map { toDto(it) }.collect(Collectors.toList())
    }
}