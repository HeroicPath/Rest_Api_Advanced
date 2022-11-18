package com.epam.esm.Rest_Api_Advanced.hateoas.assembler;

import com.epam.esm.Rest_Api_Advanced.controller.TagController;
import com.epam.esm.Rest_Api_Advanced.dto.GiftCertificateDto;
import com.epam.esm.Rest_Api_Advanced.dto.TagDto;
import com.epam.esm.Rest_Api_Advanced.model.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TagAssembler extends RepresentationModelAssemblerSupport<Tag, TagDto> {

    public TagAssembler() {
        super(TagController.class, TagDto.class);
    }

    @Override
    public TagDto toModel(Tag entity) {
        TagDto model = new TagDto();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
