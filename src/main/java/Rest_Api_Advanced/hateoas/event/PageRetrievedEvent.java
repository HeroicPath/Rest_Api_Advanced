package Rest_Api_Advanced.hateoas.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Getter
public class PageRetrievedEvent<T extends Serializable> extends ApplicationEvent {

    private final UriComponentsBuilder uriComponentsBuilder;
    private final HttpServletResponse httpServletResponse;
    private final int page;
    private final int totalPages;
    private final int size;

    public PageRetrievedEvent(Class<T> clazz, UriComponentsBuilder uriComponentsBuilder, HttpServletResponse httpServletResponse, int page, int totalPages, int size) {
        super(clazz);

        this.uriComponentsBuilder = uriComponentsBuilder;
        this.httpServletResponse = httpServletResponse;
        this.page = page;
        this.totalPages = totalPages;
        this.size = size;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getClazz(){
        return (Class<T>) getSource();
    }
}
