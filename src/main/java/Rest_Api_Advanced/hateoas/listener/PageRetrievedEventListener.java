package Rest_Api_Advanced.hateoas.listener;

import Rest_Api_Advanced.hateoas.event.PageRetrievedEvent;
import lombok.Data;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.StringJoiner;

@Component
@Data
@SuppressWarnings({"rawtypes"})
public class PageRetrievedEventListener implements ApplicationListener<PageRetrievedEvent> {

    @Override
    public void onApplicationEvent(PageRetrievedEvent event) {
        addLinkHeaderOnPageRetrieval(
                event.getUriComponentsBuilder(),
                event.getHttpServletResponse(),
                event.getClazz(),
                event.getPage(),
                event.getSize(),
                event.getTotalPages());
    }

    private void addLinkHeaderOnPageRetrieval(UriComponentsBuilder uriComponentsBuilder,
                                              HttpServletResponse httpServletResponse,
                                              Class clazz,
                                              int page,
                                              int pageSize,
                                              int totalPages) {
        addPluralityToPath(uriComponentsBuilder, clazz);
        final StringJoiner linkHeader = new StringJoiner(", ");

        if (hasNextPage(page, totalPages)) {
            String uriForNextPage = constructPageUri(uriComponentsBuilder, page + 1, pageSize);
            String uriForLastPage = constructPageUri(uriComponentsBuilder, totalPages - 1, pageSize);
            linkHeader.add(createLinkHeader(uriForNextPage, "next"));
            linkHeader.add(createLinkHeader(uriForLastPage, "last"));
        }
        if (hasPrevPage(page)){
            String uriForPrevPage = constructPageUri(uriComponentsBuilder, page - 1, pageSize);
            String uriForFirstPage = constructPageUri(uriComponentsBuilder, 0, pageSize);
            linkHeader.add(createLinkHeader(uriForPrevPage, "prev"));
            linkHeader.add(createLinkHeader(uriForFirstPage, "first"));
        }

        httpServletResponse.addHeader("Link", linkHeader.toString());
    }

    private boolean hasPrevPage(int page) {
        return page > 0;
    }

    private boolean hasNextPage(int page, int totalPages) {
        return page < (totalPages - 1);
    }

    private String constructPageUri(UriComponentsBuilder uriComponentsBuilder, int page, int pageSize) {
        return uriComponentsBuilder.replaceQueryParam("page", page)
                .replaceQueryParam("size", pageSize)
                .build()
                .encode()
                .toUriString();
    }

    private CharSequence createLinkHeader(String uri, String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }

    @SuppressWarnings("all")
    private void addPluralityToPath(UriComponentsBuilder uriComponentsBuilder, Class clazz) {
        StringBuilder resourceName = new StringBuilder("/").append(clazz.getSimpleName().toLowerCase()).append("s");
        uriComponentsBuilder.path(resourceName.toString());
    }
}
