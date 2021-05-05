import systems.danger.kotlin.*

danger(args) {

    onGitHub {
        message("Thank you so much for your work here @${pullRequest.user.login} 🎉\nYou might find a few suggestions from me for this Pull Request below 🙂")

        if (pullRequest.body.isEmpty()) {
            warn("You should provide a summary in the Pull Request description so that the reviewer has more context on this Pull Request 💚")
        }

        if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 300) {
            warn("This Pull Request is quite a big one! Maybe try splitting this into separate tasks next time 😅")
        }
    }
}
