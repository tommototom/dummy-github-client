package com.github.app.util;

import android.util.Log;
import com.github.app.model.Commit;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CommitCustomGsonDeserializer implements JsonDeserializer<Commit> {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public Commit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject commitJson = (JsonObject) json.getAsJsonObject();
            final String htmlUrl = commitJson.get("html_url").getAsString();
            final String sha = commitJson.get("sha").getAsString();

            final JsonObject inner = commitJson.get("commit").getAsJsonObject();
            final String message = inner.get("message").getAsString();

            final JsonElement committer = inner.get("committer");
            final String author = committer.getAsJsonObject().get("name").getAsString();
            final String dateString = committer.getAsJsonObject().get("date").getAsString();
            final Long date = sdf.parse(dateString).getTime();

            return Commit.Builder.aCommit()
                    .withHtmlUrl(htmlUrl)
                    .withSha(sha)
                    .withMessage(message)
                    .withAuthor(author)
                    .withMessage(message)
                    .withDate(date)
                    .build();
        } catch (Exception e) {
            Log.e("Json Parse", "Error parsing Commit message's json: ");
            e.printStackTrace();
            return null; //todo return template?
        }
    }
}
