package prosecutor.barrister.report;

import java.util.*;

/**
 * Created by Fry on 01.07.2016.
 */
public class TrialReport {
    private Map<String,String> metadata=new HashMap<>();

    public void addMetadata(String key, String value)
    {
        metadata.put(key,value);
    }
    public void addGroup()
    {

    }

    public static class TrialGroupReport
    {
        List<TrialGroupMember> members=new ArrayList<>();

        public void addMember(TrialGroupMember member)
        {
            this.members.add(member);
        }

        public static class TrialGroupMember
        {
            public String SubmissionID;
            public float Probabality;
        }
    }
}
