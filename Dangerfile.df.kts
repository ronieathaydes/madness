@file:Suppress("MaxLineLength", "MagicNumber")
@file:DependsOn("com.gianluz:danger-kotlin-android-lint-plugin:0.1.0")
@file:DependsOn("io.github.ackeecz:danger-kotlin-detekt:0.1.4")

import com.gianluz.dangerkotlin.androidlint.AndroidLint
import io.github.ackeecz.danger.detekt.DetektPlugin

import systems.danger.kotlin.danger
import systems.danger.kotlin.fail
import systems.danger.kotlin.message
import systems.danger.kotlin.onGitHub
import systems.danger.kotlin.register
import systems.danger.kotlin.warn

import java.io.File

register plugin AndroidLint
register plugin DetektPlugin

danger(args) {

    onGitHub {
        message("Thank you so much for your work here @${pullRequest.user.login} 🎉 \nYou might find a few suggestions from me for this Pull Request below 🙂")

        if (pullRequest.title.contains("WIP", false)) {
            fail("I noticed this is marked as Work In Progress, does it need to be open before it is ready? 🤔 \nIf so, convert it to a Draft Pull Request and remove WIP from title 😉")
        }

        if (pullRequest.body.isEmpty()) {
            warn("You should provide a summary in the description so that the reviewer has more context on this Pull Request 💚")
        }

        if (pullRequest.assignees.isEmpty()) {
            fail("This Pull Request does not have any assignees yet 🧐")
        }

        if (commits.any { githubCommit -> githubCommit.commit.message.startsWith("Merge branch '${pullRequest.base.ref}'") }) {
            fail("Please rebase to get rid of the merge commits in this Pull Request 🙃")
        }

        if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 300) {
            warn("This Pull Request is quite a big one! Maybe try splitting this into separate tasks next time 😅")
        }
    }

    AndroidLint.report("lint-results-debug.xml")

    DetektPlugin.parseAndReport(File("detekt-report.xml"))
}
