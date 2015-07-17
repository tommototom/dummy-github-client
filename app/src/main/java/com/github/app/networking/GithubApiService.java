package com.github.app.networking;

import com.github.app.model.Commit;
import com.github.app.model.Owner;
import com.github.app.model.Repository;
import com.github.app.util.Constants;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface GithubApiService {

    @GET("/users/{username}")
    Owner getOwnerInfo(@Path("username") String owner);

    @GET("/users/{username}/repos?per_page=" + Constants.REPOS_PER_PAGE)
    List<Repository> getRepositoriesList(
            @Path("username") String owner,
            @Query("page") int pageNum);

    @GET("/repos/{username}/{reponame}/commits?per_page=" + Constants.COMMITS_PER_PAGE)
    List<Commit> getRepositoryCommit(
            @Path("username") String owner,
            @Path("reponame") String repositoryName,
            @Query("page") int pageNum);
}
