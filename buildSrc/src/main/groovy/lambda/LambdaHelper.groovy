package lambda

import com.amazonaws.SdkClientException
import groovy.transform.CompileStatic
import jp.classmethod.aws.gradle.AwsPluginExtension
import org.gradle.api.Project

@CompileStatic
class LambdaHelper {

    private LambdaHelper() { }

    // see https://github.com/classmethod/gradle-aws-plugin/pull/160
    static String getAwsAccountId(Project project) {
        try {
            return project.getExtensions().getByType(AwsPluginExtension).accountId
        } catch (SdkClientException ignored) {
            project.logger.lifecycle("AWS credentials not configured!")
            return '000000000000'
        }
    }

}
