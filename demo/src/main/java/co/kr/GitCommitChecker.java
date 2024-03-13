package co.kr;

import org.springframework.stereotype.Service;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class GitCommitChecker {
    public static void main(String[] args) {
        // Path to the Git repository
        String repoPath = "C:\\Users\\qsw233\\Desktop\\project\\git_alarm";

        // Author name to filter commits
        String authorName = "choss001";

        try {
            // Open the Git repository
            Repository repository = new FileRepositoryBuilder()
                    .setGitDir(new File(repoPath + "/.git"))
                    .build();

            // Create a Git object
            Git git = new Git(repository);

            // Get the commits
            Iterable<RevCommit> commits = git.log().call();

            // Get today's date and yesterday's date
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

            // Iterate over the commits
            for (RevCommit commit : commits) {
                Date commitDate = commit.getAuthorIdent().getWhen();
                LocalDate commitLocalDate = commitDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Check if the commit was made today or yesterday by the specified author
                if (commitLocalDate.equals(today) || commitLocalDate.equals(yesterday)) {
                    if (commit.getAuthorIdent().getName().equals(authorName)) {
                        System.out.println("Commit " + commit.getId().getName() + " by " + authorName + " on " + commitLocalDate);
                    }
                }
            }

            // Close the repository
            repository.close();
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }

}
