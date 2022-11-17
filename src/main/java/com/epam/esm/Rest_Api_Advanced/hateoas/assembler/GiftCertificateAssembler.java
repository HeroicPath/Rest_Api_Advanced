package com.epam.esm.Rest_Api_Advanced.hateoas.assembler;

import com.epam.esm.Rest_Api_Advanced.controller.GiftCertificateController;
import com.epam.esm.Rest_Api_Advanced.dto.GiftCertificateDto;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
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
