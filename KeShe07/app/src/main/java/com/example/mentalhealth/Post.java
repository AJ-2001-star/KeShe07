package com.example.mentalhealth;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String id;
    private String username;
    private String content;
    private long timestamp;

    public Post(String id, String username, String content, long timestamp) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }

    protected Post(Parcel in) {
        id = in.readString();
        username = in.readString();
        content = in.readString();
        timestamp = in.readLong();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(content);
        dest.writeLong(timestamp);
    }
}
