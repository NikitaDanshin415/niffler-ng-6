package guru.qa.niffler.jupiter.issue;

import guru.qa.niffler.api.github.GitHubApiClient;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.SearchOption;

import java.io.IOException;

public class IssueExtension implements ExecutionCondition {
    private final GitHubApiClient gitHubApiClient = new GitHubApiClient();

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        return AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                DisabledByIssue.class
        ).or(() -> AnnotationSupport
                .findAnnotation(
                        context.getRequiredTestClass(),
                        DisabledByIssue.class,
                        SearchOption.INCLUDE_ENCLOSING_CLASSES
                )
        ).map(
                byIssue -> {
                    try {
                        return "open".equals(gitHubApiClient.issueState(byIssue.value()))
                        ? ConditionEvaluationResult.disabled("Disabled by issue: " + byIssue.value() )
                                : ConditionEvaluationResult.enabled("Issue closed");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).orElseGet(
                () -> ConditionEvaluationResult.enabled("Annotation @DisabledByIssue not found")
        );
    }
}
