package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.repository.POSRepository;
import by.c7d5a6.languageparser.rest.model.POS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class POSService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final POSRepository posRepository;

    @Autowired
    public POSService(POSRepository posRepository) {
        this.posRepository = posRepository;
    }

    public List<POS> getAllPOS() {
        return posRepository.findAll().stream().map(this::convertToPOS).collect(Collectors.toList());
    }

    private POS convertToPOS(EPOS epos) {
        return mapper.map(epos, POS.class);
    }
}
