package pl.dkatszer.inz.apigateway.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by doka on 2017-12-16.
 *
 * For example we can add header to request
 */
@Component
public class SimpleFilter extends ZuulFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    public String filterType() {
        return FilterType.PRE.value();
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        LOGGER.info(String.format("Received %s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }
}
