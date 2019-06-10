import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.cft.focus.model.Credentials;
import ru.cft.focus.api.GitHubApi;
import ru.cft.focus.model.Comment;
import ru.cft.focus.model.Issue;
import ru.cft.focus.model.IssueLocker;
import ru.cft.focus.model.Milestone;
import ru.cft.focus.model.Repo;
import ru.cft.focus.utils.Utils;

import java.util.List;
import java.util.Map;

public class GitHubTest {

    private static final String USER = "qafsMainTestUser";
    private static final String TEST_REPO_NAME = "newRepo";

    private static GitHubApi mainUser;
    private static GitHubApi extraUser;

    @BeforeAll
    public static void prepareRepoAndUsers() {
        //create users
        Credentials mainUserCredentials = Utils.jsonToModelCredentials(Utils.getResourceAsString("/mainUserCredentialsJson.json"));
        Credentials extraUserCredentials = Utils.jsonToModelCredentials(Utils.getResourceAsString("/extraUserCredentialsJson.json"));
        mainUser = new GitHubApi(USER, TEST_REPO_NAME, mainUserCredentials);
        extraUser = new GitHubApi(USER, TEST_REPO_NAME, extraUserCredentials);

        //create repo with milestone
        Repo repo = new Repo(TEST_REPO_NAME, "Some description");
        Milestone milestone = new Milestone(
                "v1.0",
                "open",
                "Tracking milestone for version 1.0",
                "2030-10-09T23:39:01Z");
        mainUser.createRepo(repo);
        mainUser.createMilestone(milestone);
    }

    @AfterAll
    public static void clearRepo() {
        mainUser.deleteRepo();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "testIssue.json"
    })
    public void testCreateIssue(String jsonFileName) {
        Issue testIssue = Utils.jsonToModelIssue(Utils.getResourceAsString("/" + jsonFileName));

        //create issue
        Integer createdId = mainUser.createIssue(testIssue).get("id");

        //check issue
        List<Map> issues = mainUser.getIssues().get();
        for (Map issue : issues) {
            if (createdId.equals(issue.get("id"))) {
                return;
            }
        }

        Assertions.fail("There are no issues with expected id - " + createdId);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "testIssue.json"
    })
    public void testEditIssue(String jsonFileName) {
        Issue testIssue = Utils.jsonToModelIssue(Utils.getResourceAsString("/" + jsonFileName));

        //create issue
        Integer createdNumber = mainUser.createIssue(testIssue).get("number");

        //patch issue
        testIssue.body += "newDesc";
        mainUser.patchIssue(testIssue, createdNumber);

        //check issue
        String patchedBody = mainUser.getIssue(createdNumber).get("body");
        Assertions.assertEquals(testIssue.body, patchedBody);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "testIssue.json"
    })
    public void testLockIssue(String jsonFileName) {
        Issue testIssue = Utils.jsonToModelIssue(Utils.getResourceAsString("/" + jsonFileName));
        IssueLocker issueLocker = new IssueLocker("resolved", true);
        Comment successfulComment = new Comment("successful comment");
        Comment lockedComment = new Comment("locked comment");

        //create issue
        Integer createdNumber = mainUser.createIssue(testIssue).get("number");

        //post successful comment
        extraUser.commentIssue(createdNumber, successfulComment);

        //lock issue
        mainUser.lockIssue(createdNumber, issueLocker);

        //post locked comment
        Assertions.assertThrows(AssertionError.class, () -> extraUser.commentIssue(createdNumber, lockedComment));
    }
}
