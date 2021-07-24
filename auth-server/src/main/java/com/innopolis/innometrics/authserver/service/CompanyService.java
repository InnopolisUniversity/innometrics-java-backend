package com.innopolis.innometrics.authserver.service;

import com.innopolis.innometrics.authserver.DTO.CompanyListRequest;
import com.innopolis.innometrics.authserver.DTO.CompanyRequest;
import com.innopolis.innometrics.authserver.entitiy.Company;
import com.innopolis.innometrics.authserver.repository.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Component
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public boolean existsById(Integer id) {
        return companyRepository.existsByCompanyid(id);
    }

    public CompanyRequest create(CompanyRequest detail) {
        Company entity = new Company();
        BeanUtils.copyProperties(detail, entity);
        entity = companyRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity, detail);

        return detail;
    }

    public CompanyRequest update(CompanyRequest detail) {
        Company entity = companyRepository.findByCompanyid(detail.getCompanyid());
        assertNotNull(entity,
                "No company found by this id " + detail.getCompanyid());
        detail.setCompanyid(null);
        BeanUtils.copyProperties(detail, entity, getNullPropertyNames(detail));
        entity = companyRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity, detail);

        return detail;
    }

    public void delete(Integer id) throws Exception {

        Company entity = companyRepository.findById(id).orElse(null);
        assertNotNull(entity,
                "No Company found by id " + id);


        companyRepository.delete(entity);
    }

    public CompanyRequest findByCompanyId(Integer id) {
        Company entity = companyRepository.findByCompanyid(id);

        assertNotNull(entity,
                "No company found by this id " + id);

        CompanyRequest detail = new CompanyRequest();

        BeanUtils.copyProperties(entity, detail);

        return detail;

    }

    public CompanyListRequest findAllActiveCompanies() {
        List<Company> activeCompanies = companyRepository.findAllActive();
        return convertFromList(activeCompanies);
    }

    public CompanyListRequest findAllCompanies() {
        List<Company> allCompanies = companyRepository.findAll();
        return convertFromList(allCompanies);
    }

    private CompanyListRequest convertFromList(List<Company> companyList) {
        CompanyListRequest companyListRequest = new CompanyListRequest();
        for (Company activeCompany : companyList) {
            CompanyRequest detail = new CompanyRequest();

            BeanUtils.copyProperties(activeCompany, detail);
            companyListRequest.addCompanyRequest(detail);
        }

        return companyListRequest;
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set emptyNames = new HashSet();

        for (java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {

            if (src.getPropertyValue(descriptor.getName()) == null) {
                emptyNames.add(descriptor.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }
}
