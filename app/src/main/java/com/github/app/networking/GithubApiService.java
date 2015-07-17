package com.github.app.networking;

import com.github.app.model.Commit;
import com.github.app.model.Owner;
import com.github.app.model.Repository;
import com.github.app.util.Constants;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

import static com.github.app.util.Constants.*;

public interface GithubApiService {

    @GET("/users/{username}")
    Owner getOwnerInfo(@Path("username") String owner);


    @GET("/users/{username}/repos?per_page=" + REPOS_PER_PAGE)
    List<Repository> getRepositoriesList(
            @Path("username") String owner,
            @Query("page") int pageNum);


    @GET("/user/repos?per_page=" + REPOS_PER_PAGE)
    List<Repository> getUserRepositoriesList(
            @Query(ACCESS_TOKEN_KEY) String accessToken,
            @Query("page") int pageNum);


    @GET("/repos/{username}/{reponame}/commits?per_page=" + COMMITS_PER_PAGE)
    List<Commit> getRepositoryCommit(
            @Path("username") String owner,
            @Path("reponame") String repositoryName,
            @Query(ACCESS_TOKEN_KEY) String accessToken,
            @Query("page") int pageNum);
}
