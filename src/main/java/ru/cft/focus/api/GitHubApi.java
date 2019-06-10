package ru.cft.focus.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import ru.cft.focus.model.*;

public class GitHubApi {

    private final HttpMethods request;
    private final Path path;

    public GitHubApi(String userName, String repoName, Credentials credentials) {
        this.path = new Path(userName, repoName);
        this.request = new HttpMethods(path.getBaseUrl(), credentials);
    }

    public JsonPath createRepo(Repo repo) {
        Response response = request.post(path.getPathToUserRepos(), repo);
        response.then().statusCode(201);
        return response.body().jsonPath();
    }

    public JsonPath deleteRepo() {
        Response response = request.delete(path.getPathToRepo());
        response.then().statusCode(204);
        return response.body().jsonPath();
    }

    public JsonPath createIssue(Issue issue) {
        Response response = request.post(path.getPathToIssues(), issue);
        response.then().statusCode(201);
        return response.body().jsonPath();
    }

    public JsonPath patchIssue(Issue issue, Integer issueNumber) {
        Response response = request.patch(path.getPathToIssue(issueNumber), issue);
        response.then().statusCode(200);
        return response.body().jsonPath();
    }

    public JsonPath lockIssue(Integer issueNumber, IssueLocker locker) {
        Response response = request.put(path.getPathToIssueLock(issueNumber), locker);
        response.then().statusCode(204);
        return response.body().jsonPath();
    }

    public JsonPath getIssue(Integer issueNumber) {
        Response response = request.get(path.getPathToIssue(issueNumber));
        response.then().statusCode(200);
        return response.body().jsonPath();
    }

    public JsonPath getIssues() {
        Response response = request.get(path.getPathToIssues());
        response.then().statusCode(200);
        return response.body().jsonPath();
    }

    public JsonPath createMilestone(Milestone milestone) {
        Response response = request.post(path.getPathToMilestones(), milestone);
        response.then().statusCode(201);
        return response.body().jsonPath();
    }

    public JsonPath commentIssue(Integer issueNumber, Comment comment) {
        Response response = request.post(path.getPathToComments(issueNumber), comment);
        response.then().statusCode(201);
        return response.body().jsonPath();
    }
}
