package me.cafecode.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

// https://api.github.com/repos/square/retrofit/issues/2833

@Entity
data class Issue(
        @PrimaryKey @SerializedName("id") var id: Int?,
        @SerializedName("title") var title: String?,
        @SerializedName("url") var url: String?,
        @SerializedName("repository_url") var repositoryUrl: String?,
        @SerializedName("labels_url") var labelsUrl: String?,
        @SerializedName("comments_url") var commentsUrl: String?,
        @SerializedName("events_url") var eventsUrl: String?,
        @SerializedName("html_url") var htmlUrl: String?,
        @SerializedName("node_id") var nodeId: String?,
        @SerializedName("number") var number: Int?,

        /** Added prefix to User property to avoid embedded same property name in Issue model. */
        @Embedded(prefix = "user_") @SerializedName("user") var user: User?,
//        @SerializedName("labels") var labels: List<Any?>?,
        @SerializedName("state") var state: String?,
        @SerializedName("locked") var locked: Boolean?,
        @Embedded(prefix ="assignee_") @SerializedName("assignee") var assignee: User?,
        @SerializedName("assignees") var assignees: List<User?>?,
//        @SerializedName("milestone") var milestone: Any?,
        @SerializedName("comments") var comments: Int?,
        @SerializedName("created_at") var createdAt: Date?,
        @SerializedName("updated_at") var updatedAt: Date?,
        @SerializedName("closed_at") var closedAt: Date?,
        @SerializedName("author_association") var authorAssociation: String?,
        @SerializedName("body") var body: String?,
        @Embedded(prefix = "closed_by_") @SerializedName("closed_by") var closedBy: User?
)
