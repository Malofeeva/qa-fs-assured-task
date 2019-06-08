package ru.cft.focus.api;

class Path {

    private static final String BASE_URL = "https://api.github.com";
    private static final String PATH_TO_USER_REPOS = "/user/repos";
    private static final String REPOS = "repos";
    private static final String ISSUES = "issues";
    private static final String MILESTONES = "milestones";
    private static final String LOCK = "lock";
    private static final String COMMENTS = "comments";

    private final String user;
    private final String repoName;

    Path(String user, String repoName) {
        this.user = user;
        this.repoName = repoName;
    }

    private String getPath(String... args) {
        return String.join("/", args);
    }

    String getBaseUrl() {
        return BASE_URL;
    }

    String getPathToUserRepos() {
        return PATH_TO_USER_REPOS;
    }

    String getPathToRepo() {
        return getPath(REPOS, user, repoName);
    }

    String getPathToIssues() {
        return getPath(getPathToRepo(), ISSUES);
    }

    String getPathToIssue(Integer issueNumber) {
        return getPath(getPathToIssues(), issueNumber.toString());
    }

    String getPathToMilestones() {
        return getPath(getPathToRepo(), MILESTONES);
    }

    String getPathToIssueLock(Integer issueNumber) {
        return getPath(getPathToIssue(issueNumber), LOCK);
    }

    String getPathToComments(Integer issueNumber) {
        return getPath(getPathToIssue(issueNumber), COMMENTS);
    }
}
