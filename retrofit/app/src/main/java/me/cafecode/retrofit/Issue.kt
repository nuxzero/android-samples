package me.cafecode.retrofit

import com.google.gson.annotations.SerializedName
import java.util.*

// https://api.github.com/repos/square/retrofit/issues/2833
data class Issue(
        @SerializedName("url") var url: String?,
        @SerializedName("repository_url") var repositoryUrl: String?,
        @SerializedName("labels_url") var labelsUrl: String?,
        @SerializedName("comments_url") var commentsUrl: String?,
        @SerializedName("events_url") var eventsUrl: String?,
        @SerializedName("html_url") var htmlUrl: String?,
        @SerializedName("id") var id: Int?,
        @SerializedName("node_id") var nodeId: String?,
        @SerializedName("number") var number: Int?,
        @SerializedName("title") var title: String?,
        @SerializedName("user") var user: User?,
//        @SerializedName("labels") var labels: List<Any?>?,
        @SerializedName("state") var state: String?,
        @SerializedName("locked") var locked: Boolean?,
//        @SerializedName("assignee") var assignee: Any?,
//        @SerializedName("assignees") var assignees: List<Any?>?,
//        @SerializedName("milestone") var milestone: Any?,
        @SerializedName("comments") var comments: Int?,
        @SerializedName("created_at") var createdAt: Date?,
        @SerializedName("updated_at") var updatedAt: Date?,
        @SerializedName("closed_at") var closedAt: Date?,
        @SerializedName("author_association") var authorAssociation: String?,
        @SerializedName("body") var body: String?,
        @SerializedName("closed_by") var closedBy: User?
)
