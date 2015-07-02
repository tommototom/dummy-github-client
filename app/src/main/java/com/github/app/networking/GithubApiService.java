package com.github.app.networking;

import com.github.app.model.Commit;
import com.github.app.model.Owner;
import com.github.app.model.Repository;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface GithubApiService {

    @GET("/users/{username}")
    Owner getOwnerInfo(@Path("username") String owner);

    @GET("/users/{username}/repos") // todo pagination 'https://api.github.com/user/repos?page=2&per_page=100'
    List<Repository> getRepositoriesList(@Path("username") String owner);

    @GET("/repos/{username}/{reponame}/commits")
    List<Commit> getRepositoryCommit(
            @Path("username") String owner,
            @Path("reponame") String repositoryName
    );
}
