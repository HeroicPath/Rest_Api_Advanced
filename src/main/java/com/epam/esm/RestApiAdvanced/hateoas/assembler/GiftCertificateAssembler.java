package com.epam.esm.RestApiAdvanced.hateoas.assembler;

import com.epam.esm.RestApiAdvanced.controller.GiftCertificateController;
import com.epam.esm.RestApiAdvanced.dto.GiftCertificateDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateAssembler extends RepresentationModelAssemblerSupport<GiftCertificate, GiftCertificateDto> {

    public GiftCertificateAssembler() {
        super(GiftCertificateController.class, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto toModel(GiftCertificate entity) {
        GiftCertificateDto model = new GiftCertificateDto();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
