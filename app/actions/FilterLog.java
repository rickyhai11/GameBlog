package actions;

import akka.stream.Materializer;
import play.Logger;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Implement log filter that intercepts all HTTP requests, then display useful request info to Logger.
 */
public class FilterLog extends Filter {

    @Inject
    public FilterLog(Materializer mat) {
        super(mat);
    }

    @Override
    public CompletionStage<Result> apply(
            Function<Http.RequestHeader, CompletionStage<Result>> nextFilter, Http.RequestHeader requestHeader) {
        long startTime = System.currentTimeMillis();
        return nextFilter.apply(requestHeader).thenApply(result -> {
            long endTime = System.currentTimeMillis();
            long requestTime = endTime - startTime;

            if (!requestHeader.uri().contains("assets")) {
                Logger.info("{} {} from {} took {}ms and returned {}",
                        requestHeader.method(), requestHeader.uri(), requestHeader.remoteAddress(), requestTime, result.status());
            }

            return result;
        });

    }
}