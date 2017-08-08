package common;

import java.util.Stack;

import common.libraries.Singleton;


public class ActivityStack extends Singleton {

    private Stack<Activity_> stack = null;
    public ActivityStack() {
        stack = new Stack<>();
    }

    public void addStack(Activity_ activity) {
        String key = activity.getClass().getSimpleName();
        stack.push(activity);
    }

    public Activity_ getTop() {
        return stack.peek();
    }

    public void remStack(Activity_ activity) {
        stack.remove(activity);
    }
}
