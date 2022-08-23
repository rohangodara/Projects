package gitlet;

import java.io.File;
import static gitlet.Utils.*;

import java.net.FileNameMap;
import java.util.*;


// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Prithvi Singh & Rohan Godara
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir")); //does it create it automatically when init
    //is called or do we need to put it inside the function?
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */

    public static final File STAGING_AREA = join(GITLET_DIR, "stagingArea");

    public static final File COMMITS = join(GITLET_DIR, "commits");

    public static final File BLOBS = join(GITLET_DIR, "blobs");

    public static final File REFERENCES = join(GITLET_DIR, "references");

    public static final File addition = join(STAGING_AREA, "addition");

    public static final File removal = join(STAGING_AREA, "removal");
    
    public static Commit HEAD;

    public static HashMap<String, String> addyTracker;

    public static HashMap<String, String> byebyeTracker;

    public static HashMap<String, Commit> branches;

    public static String currentBranch;

    public static File currentBranchLocation = join(REFERENCES, "currentBranchTracker");

    public static File addyTrackerLocation = join(REFERENCES, "addedTracker"); //location of addyTracker

    public static File byebyeTrackerLocation = join(REFERENCES, "removedTracker"); //location of byebyeTracker

    public static File branchesLocation = join(REFERENCES, "branchesTracker");


    public void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory."); return;
        } else {
            GITLET_DIR.mkdir(); //creates folders
            COMMITS.mkdir();
            STAGING_AREA.mkdir();
            BLOBS.mkdir();
            addition.mkdir();
            removal.mkdir();
            REFERENCES.mkdir();
            addyTracker = new HashMap<String, String>();
            byebyeTracker = new HashMap<String, String>();
            branches = new HashMap<String, Commit>();
            serializeAddyTracker(); //creates addyTracker
            serializebyebyeTracker();
            Date startDate = new Date(0);
            HashMap<String, Blob> tracked = new HashMap<>();
            Commit initial = new Commit("initial commit", null, startDate, tracked);
            branches.put("master", initial);
            currentBranch = "master";
            HEAD = initial;
            serializeCurrentBranch();
            serializeBranches();
            serializeCommit(initial);
            serializeHEAD();
        }
    }



    public void add(String fileName) {
        //if file does not exist { print "File does not exist." }
        //if cwd file == version in commits(same contents) { (using sha1)
        //  dont add
        //  remove from staging area if already there }
        //add copy of file from WD to staging area(addition)
        //check if latest commit has pointer to sha1 code of concerned file

        //File copied = new File(fileName); //location of fileName
        byebyeTracker=Utils.readObject(byebyeTrackerLocation, HashMap.class);
        if(byebyeTracker.containsKey(fileName)) {
            File newby = join(CWD, fileName);
            Utils.writeContents(newby, readContentsAsString(join(removal, fileName)));
            join(removal, fileName).delete();
            byebyeTracker.remove(fileName);
            serializebyebyeTracker();
            return;
        }

        if (!join(CWD, fileName).exists()) { //file doesn't exist in WD
            System.out.println("File does not exist."); return;
        } else { //File does exist in WD
            addyTracker = Utils.readObject(addyTrackerLocation, HashMap.class); //brings back addition folder hashmap
            String copiedSha = sha1(readContentsAsString(join(CWD, fileName)));
            HEAD = Utils.readObject(join(REFERENCES, "HEAD"),Commit.class);
            if (HEAD.getTrackedBlobs().containsKey(fileName)) {
                if (copiedSha.equals(HEAD.getTrackedBlobs().get(fileName).getSHA1Name())) {
                    return;
                }
            }
            if (!addyTracker.containsKey(fileName)) { //if hashmap doesnt contain this key (filename) we give it a new key
                addyTracker.put(fileName, copiedSha);
                serializeAddyTracker(); //we take this new hashmap and add it to the hashmap file in the addition folder
                File newStaged = Utils.join(addition, fileName); //location of copied file // creates new file of required name; adds file to addition folder
                Utils.writeContents(newStaged, readContents(join(CWD, fileName))); // add contents of 'filename' to this new file //CHECK if it gets file's contents
            } else {
                addyTracker.put(fileName, copiedSha);
                serializeAddyTracker();
                Utils.writeContents(join(addition, fileName), readContents(join(CWD, fileName)));
            }
        }
    }

    public void commit(String message) {
        if (message.equals("")) {
            System.out.println("Please enter a commit message."); return;
        }
        if (addition.list().length==0 && removal.list().length == 0) { //https://stackabuse.com/java-check-if-file-or-directory-is-empty
            System.out.println("No changes added to the commit."); return;
        }
        if (addition.list().length==0 && removal.list().length != 0) { //https://stackabuse.com/java-check-if-file-or-directory-is-empty
            clearStage();
            addyTracker = Utils.readObject(addyTrackerLocation, HashMap.class);
            byebyeTracker = Utils.readObject(byebyeTrackerLocation, HashMap.class);
            addyTracker.clear();
            byebyeTracker.clear();
            serializeAddyTracker();
            serializebyebyeTracker();
            return;
        }

        Date currentDate = new Date();
        HEAD = Utils.readObject(join(REFERENCES, "HEAD"),Commit.class);
        branches = Utils.readObject(branchesLocation, HashMap.class);
        currentBranch = Utils.readObject(currentBranchLocation, String.class);
        addyTracker = Utils.readObject(addyTrackerLocation, HashMap.class);
        byebyeTracker = Utils.readObject(byebyeTrackerLocation, HashMap.class);
        HashMap<String, Blob> newCommitTracker = trackedBlobSorter(HEAD.getTrackedBlobs(), addyTracker, byebyeTracker);
        Commit newby = new Commit(message, sha1(serialize(HEAD)), currentDate, newCommitTracker);
        branches.put(currentBranch, newby);
        HEAD = newby;
        addyTracker.clear();
        byebyeTracker.clear();
        serializeHEAD();
        serializeCommit(newby);
        serializeBranches();
        serializeCurrentBranch();
        clearStage();
        clearTrackers();
        serializeAddyTracker();
        serializebyebyeTracker();
        /**The commit just made becomes the “current commit”,
         and the head pointer now points to it.
         The previous head commit is this commit’s parent commit.*/
        //clear staging area
        //SHA-1 id, which must include the file
        // (blob) references of its files, parent reference, log message, and commit time.
    }



    public void log() {
        HEAD=Utils.readObject(join(REFERENCES, "HEAD"),Commit.class);
        Commit logged= HEAD;
        //print testing
        //System.out.println(logged.getParentHash());

        while(logged.getParentHash()!=null){
            System.out.println("===");
            System.out.println("commit " + sha1(serialize(logged)));
            System.out.println("Date: " + logged.getTime());
            System.out.println(logged.getMessage());
            System.out.println("");
            logged = Utils.readObject(join(COMMITS, logged.getParentHash()),Commit.class);
        }
        System.out.println("===");
        System.out.println("commit " + sha1(serialize(logged)));
        System.out.println("Date: " + logged.getTime());
        System.out.println(logged.getMessage());
        System.out.println("");
    }

    public void globallog() {
        for (int a = 0; a < plainFilenamesIn(COMMITS).size(); a++) {
            Commit logged = Utils.readObject(join(COMMITS, plainFilenamesIn(COMMITS).get(a)), Commit.class);
            System.out.println("===");
            System.out.println("commit " + sha1(serialize(logged)));
            System.out.println("Date: " + logged.getTime());
            System.out.println(logged.getMessage());
            System.out.println("");
        }
    }

    public void find(String wantedMessage) {
        int count = 0;
        for (int a = 0; a < plainFilenamesIn(COMMITS).size(); a++) {
            Commit logged = Utils.readObject(join(COMMITS, plainFilenamesIn(COMMITS).get(a)), Commit.class);
            if (logged.getMessage() == wantedMessage) {
                System.out.println(sha1(serialize(logged)));
                count ++;
            }
        }
        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }
    }
    // for (String key : added.keySet())
    public void status() {
        branches = Utils.readObject(branchesLocation, HashMap.class);
        addyTracker = Utils.readObject(addyTrackerLocation, HashMap.class);
        byebyeTracker = Utils.readObject(byebyeTrackerLocation, HashMap.class);
        currentBranch = Utils.readObject(currentBranchLocation, String.class);
        ArrayList<String> branchesAL = sortHashMapKeys(branches.keySet());
        branchesAL.remove(currentBranch);
        ArrayList<String> addyAL = sortHashMapKeys(addyTracker.keySet());
        ArrayList<String> byebyeAL = sortHashMapKeys(byebyeTracker.keySet());

        System.out.println("=== Branches ===");
        System.out.println("*" + currentBranch);
        for (String x : branchesAL) {
            if (x.equals(currentBranch)) {
                continue; //
            }
            System.out.println(x);
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for (String x : addyAL) {
            System.out.println(x);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (String x : byebyeAL) {
            System.out.println(x);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public void checkout1(String fileName){
        HEAD = Utils.readObject(join(REFERENCES, "HEAD"),Commit.class);
        if(!HEAD.getTrackedBlobs().containsKey(fileName)){System.out.println("File does not exist in that commit.");return;}
        byte[] targetContent = HEAD.getTrackedBlobs().get(fileName).getInside(); //CHECK
        if (join(CWD, fileName).exists()) {
            Utils.writeContents(join(CWD,fileName), targetContent);
        } else {
            File f= join(CWD,fileName);
            Utils.writeContents(f, targetContent);
        }
    }

    public void checkout2(String Shah1name, String fileName){
        String a = Shah1name;
        if (Shah1name.length() == 6) {
            Boolean DoesShaExist = false;
            if (COMMITS.listFiles() != null) {
                for (File file : COMMITS.listFiles()) {
                    if (file.getName().substring(0, 5).equals(Shah1name)) {
                        a = file.getName(); DoesShaExist = true;
                    }
                }
                if (!DoesShaExist){System.out.println("No commit with that id exists."); return;}
            }

        }
        else{if(!join(COMMITS,a).exists()){System.out.println("No commit with that id exists."); return;}}
        Commit TargetCommit=Utils.readObject(join(COMMITS, a),Commit.class);
        if(!TargetCommit.getTrackedBlobs().containsKey(fileName)){System.out.println("File does not exist in that commit."); return;}
        byte[] targetContent = TargetCommit.getTrackedBlobs().get(fileName).getInside();
        if (join(CWD, fileName).exists()) {
            Utils.writeContents(join(CWD,fileName),targetContent);
        } else {
            File f= join(CWD,fileName);
            Utils.writeContents(f,targetContent);
        }
    }

    public void checkout3(String branchy) {
        /* we have 2 branches: master and nugget. master has a file hello.txt. nugget has file hello. txt. we have a cwd with files hello and bye.
        * we do checkout(nugget)---> do we delete bye from cwd??**/
        branches = Utils.readObject(branchesLocation,HashMap.class);
        currentBranch = Utils.readObject(currentBranchLocation, String.class);
        HEAD = Utils.readObject(join(REFERENCES, "HEAD"),Commit.class);

        if(!branches.containsKey(branchy)){
            System.out.println("No such branch exists.");
            return;
        }

        Commit TargetHead = branches.get(branchy);

        if (branchy==currentBranch){
            System.out.println("No need to checkout the current branch.");
            return;
        }

        for (Map.Entry<String,Blob> x : TargetHead.getTrackedBlobs().entrySet()){
            if (join(CWD, x.getKey()).exists()){
                if(HEAD.getTrackedBlobs().containsKey(x.getKey())){
                    if(HEAD.getTrackedBlobs().get(x.getKey()).getSHA1Name() != sha1(readContents(join(CWD, x.getKey())))){   //sha1(join(CWD, x.getkey())))
                        System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                        return;
                    } else {
                        Utils.writeContents(join(CWD,x.getKey()),x.getValue().getInside());} //are we doing write contents or objects
                } else {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first."); /**CHECK */
                    return;
                }
            } else {
                File f= join(CWD,x.getKey());
                Utils.writeContents(f,x.getValue().getInside()); //write contents or objects
            }
        }

        for (Map.Entry<String,Blob> x : HEAD.getTrackedBlobs().entrySet()) {
            if (!TargetHead.getTrackedBlobs().containsKey(x.getKey())) {
                if (join(CWD,x.getKey()).exists()) {
                    join(CWD,x.getKey()).delete();
                }
            }
        }
        HEAD=TargetHead;
        serializeHEAD();
        clearStage();
        currentBranch=branchy;
        serializeCurrentBranch();
    }


    public void rm(String fileName) {
        //if in addytracker, remove from addytracker and from addition folder
        // else if fileName in current commit( i.e trackedBlobs), stage for removal(method?), remove from WD(method?)
        // else print "No reason to remove the file."
        addyTracker = Utils.readObject(addyTrackerLocation, HashMap.class);
        byebyeTracker = Utils.readObject(byebyeTrackerLocation, HashMap.class);
        HEAD = Utils.readObject(join(REFERENCES, "HEAD"), Commit.class);
        if (!addyTracker.containsKey(fileName) && !HEAD.getTrackedBlobs().containsKey(fileName)) {  /** maybe about contents and not filename? */
            System.out.println("No reason to remove the file.");
            return;
        }
        if (addyTracker.containsKey(fileName)) {
            addyTracker.remove(fileName);
            serializeAddyTracker();
            fileRemover(addition, fileName);
        }
        if (HEAD.getTrackedBlobs().containsKey(fileName)) {
            if (!join(CWD, fileName).exists()) {
                addyTracker.remove(fileName);
                serializeAddyTracker();
                fileRemover(addition, fileName);
            }
            File newStaged =  join(removal, fileName);
            File currentver = join(CWD, fileName);
            if (currentver.isFile() && !currentver.isDirectory()) {
                Utils.writeObject(newStaged, readContentsAsString(currentver));
            }

            String copiedSha = sha1(readContentsAsString(currentver));
            byebyeTracker.put(fileName, copiedSha);
            serializebyebyeTracker();
            fileRemover(CWD, fileName);
        }
    }

    public void branch(String branchName) {
        branches = Utils.readObject(branchesLocation, HashMap.class);
        HEAD = Utils.readObject(join(REFERENCES, "HEAD"),Commit.class);
        if (branches.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
            return;
        } else {
            branches.put(branchName, HEAD);
            serializeBranches();
        }
    }



    public void rmBranch(String givenName) {
        branches = Utils.readObject(branchesLocation, HashMap.class);
        currentBranch = Utils.readObject(currentBranchLocation, String.class);
        if (!branches.containsKey(givenName)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (currentBranch.equals(givenName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        branches.remove(givenName);
        serializeBranches();
    }

    public void reset(String commitID) {
        HEAD = Utils.readObject(join(REFERENCES, "HEAD"), Commit.class);
        Commit TargetHead = null;

        if (commitID.length() == 6) {
            Boolean DoesShahExist=false;
            for (File file : COMMITS.listFiles()) {
                if (file.getName().substring(0, 5).equals(commitID)) {
                    TargetHead = Utils.readObject(file, Commit.class); DoesShahExist = true;
                }
                }
            if(!DoesShahExist){System.out.println("No commit with that id exists.");return;}
            }
        else {
            if(!join(COMMITS, commitID).exists()){System.out.println("No commit with that id exists.");return;}
            TargetHead = Utils.readObject(join(COMMITS, commitID), Commit.class);
        }
        for (Map.Entry<String,Blob> x : TargetHead.getTrackedBlobs().entrySet()) {
            if(!HEAD.getTrackedBlobs().containsKey(x.getKey())){System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");return;}
            else{if(!sha1(join(CWD,x.getKey())).equals(HEAD.getTrackedBlobs().get(x.getKey()).getSHA1Name())){System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");return;}

            checkout2(commitID, x.getKey());}
        }
        for (Map.Entry<String,Blob> x : HEAD.getTrackedBlobs().entrySet()) {
            if (!TargetHead.getTrackedBlobs().containsKey(x.getKey())) {
                if (join(CWD, x.getKey()).exists()) {
                    join(CWD, x.getKey()).delete();
                }
            }
        }
        HEAD = TargetHead;
        serializeHEAD();
        clearStage();
    }




    /** HELPER FUNCTIONS */


    /** Serializes addyTracker */
    public void serializeAddyTracker() {
        Utils.writeObject(addyTrackerLocation, addyTracker);

    }


    private void serializebyebyeTracker() {
        Utils.writeObject(byebyeTrackerLocation, byebyeTracker);
    }

    private void serializeBranches() {
        Utils.writeObject(branchesLocation, branches);
    }

    private void serializeCurrentBranch() {
        Utils.writeObject(currentBranchLocation, currentBranch);
    }


    /** Takes parents hashmap of blobs and hashmap from staging area and makes new commit's hashmap */
    public HashMap<String, Blob> trackedBlobSorter(HashMap<String, Blob> parentHash, HashMap<String, String> added, HashMap<String, String> removed) { //prep for removal
        HashMap<String, Blob> returnHash = new HashMap<>();
        if (parentHash.isEmpty()) {
            for (String key : added.keySet()) {
                Blob newBlob = new Blob(key);
                returnHash.put(key, newBlob);
                serializeBlob(newBlob);
            }
        } else {
            for (String key : parentHash.keySet()) {
                if (removed.containsKey(key)) {
                    parentHash.remove(key);
                }
            }
            for (String key : parentHash.keySet()) { //key is the actual name of the file
                if (added.containsKey(key)) {
                    if (added.get(key) != parentHash.get(key).getSHA1Name()) {
                        Blob newBlob = new Blob(key);
                        serializeBlob(newBlob);
                        returnHash.put(key, newBlob);
                    } else {
                        returnHash.put(key, parentHash.get(key));
                    }
                } else {
                    returnHash.put(key, parentHash.get(key));
                }
            }
        }
        return returnHash;
    }

    /** Serializes a blob into BLOBS under its file's SHA1 name */
    public void serializeBlob(Blob newBlob) {
        File blobs = join(BLOBS, newBlob.getSHA1Name());
        Utils.writeObject(blobs, newBlob);
    }

    public void serializeHEAD() {
        File head = join(REFERENCES, "HEAD");
        Utils.writeObject(head, HEAD);
    }

    /** Serializes a commit into COMMITS under its own SHA1 name */
    public void serializeCommit(Commit newCommit) {
        File commit = join(COMMITS, sha1(serialize(newCommit)));
        Utils.writeObject(commit, newCommit);
    }

    public void clearStage() { //https://stackoverflow.com/questions/13195797/delete-all-files-in-directory-but-not-directory-one-liner-solution/13195870
        for (File file : addition.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
        for (File file : removal.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }

    public void fileRemover(File directory, String fileName) {
        join(directory, fileName).delete();
    }

    private void clearTrackers() {
        addyTracker = new HashMap<String, String>();
        byebyeTracker = new HashMap<String, String>();
    }

    public ArrayList<String> sortHashMapKeys(Set<String> strings) {
        ArrayList<String> listOfKeys = new ArrayList<String>(strings);
        Collections.sort(listOfKeys);
        return listOfKeys;
    }


}

