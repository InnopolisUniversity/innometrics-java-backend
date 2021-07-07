package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentsxcompany;
import com.innopolis.innometrics.agentsgateway.repository.AgentsxcompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentsxcompanyService {
    @Autowired
    AgentsxcompanyRepository agentsxcompanyRepository;

    public List<Agentsxcompany> getAgentsCompanyList() {
        return this.agentsxcompanyRepository.findAll();
    }

    public List<Agentsxcompany> getAgentsCompanyByAgentId(Integer agentId) {
        List<Agentsxcompany> agentsCompanyList = this.agentsxcompanyRepository.findAll();
        List<Agentsxcompany> result = new ArrayList<>();
        for (Agentsxcompany agentsxcompany : agentsCompanyList) {
            if (agentsxcompany.getAgentid().equals(agentId)) {
                result.add(agentsxcompany);
            }
        }
        return result;
    }

    public List<Agentsxcompany> getAgentsCompanyByCompanyId(Integer companyId) {
        List<Agentsxcompany> agentsCompanyList = this.agentsxcompanyRepository.findAll();
        List<Agentsxcompany> result = new ArrayList<>();
        for (Agentsxcompany agentsxcompany : agentsCompanyList) {
            if (agentsxcompany.getCompanyid().equals(companyId)) {
                result.add(agentsxcompany);
            }
        }
        return result;
    }

    public Agentsxcompany getAgentsCompanyById(Integer configId) {
        return this.agentsxcompanyRepository.findById(configId).orElse(null);
    }

    public Agentsxcompany postAgentsCompany(Agentsxcompany agentsxcompany) throws Exception {
        return this.agentsxcompanyRepository.save(agentsxcompany);
    }

    public Agentsxcompany putAgentsCompany(Integer configId, Agentsxcompany agentsxcompany) throws Exception {
        return this.agentsxcompanyRepository.findById(configId).map(agentsCompany -> {
            agentsCompany.setAgentConfig(agentsxcompany.getAgentConfig());
            agentsCompany.setAgentid(agentsxcompany.getAgentid());
            // todo maybe pass external entity?
            agentsCompany.setCompanyid(agentsxcompany.getCompanyid());
            agentsCompany.setKey(agentsxcompany.getKey());
            agentsCompany.setToken(agentsxcompany.getToken());
            agentsCompany.setIsactive(agentsxcompany.getIsactive());

            return this.agentsxcompanyRepository.save(agentsCompany);
        }).orElseGet(() -> this.agentsxcompanyRepository.save(agentsxcompany));
    }

    public List<Agentsxcompany> deleteAgentsCompanyByAgentId(Integer agentId) {
        return this.deleteAgentsCompanyList(this.getAgentsCompanyByAgentId(agentId));
    }

    public List<Agentsxcompany> deleteAgentsCompanyByCompanyId(Integer companyId) {
        return this.deleteAgentsCompanyList(this.getAgentsCompanyByCompanyId(companyId));
    }

    public Agentsxcompany deleteAgentsCompanyById(Integer configId) {
        Optional<Agentsxcompany> agentsCompany = this.agentsxcompanyRepository.findById(configId);
        if (!agentsCompany.isPresent()) {
            return null;
        }
        this.agentsxcompanyRepository.deleteById(configId);
        return agentsCompany.get();
    }

    private List<Agentsxcompany> deleteAgentsCompanyList(List<Agentsxcompany> agentsCompanyList) {
        if (agentsCompanyList == null || agentsCompanyList.isEmpty()) {
            return null;
        }
        List<Agentsxcompany> deletedAgentsCompanyList = new ArrayList<>(agentsCompanyList.size());
        for (Agentsxcompany agentsxcompany : agentsCompanyList) {
            Agentsxcompany deletedAgentsCompany = this.deleteAgentsCompanyById(agentsxcompany.getConfigid());
            if (deletedAgentsCompany != null) {
                deletedAgentsCompanyList.add(deletedAgentsCompany);
            }
        }

        return deletedAgentsCompanyList;
    }
}
