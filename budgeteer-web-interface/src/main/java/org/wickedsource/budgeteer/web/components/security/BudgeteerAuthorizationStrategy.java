package org.wickedsource.budgeteer.web.components.security;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.wickedsource.budgeteer.web.BudgeteerSession;
import org.wickedsource.budgeteer.web.pages.user.login.LoginPage;

public class BudgeteerAuthorizationStrategy implements IAuthorizationStrategy {

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        NeedsLogin annotation = componentClass.getAnnotation(NeedsLogin.class);
        boolean isAnnotated = (annotation != null);
        if (!isAnnotated) {
            return true;
        } else {
            if(!BudgeteerSession.get().isLoggedIn()) {
                throw new RestartResponseAtInterceptPageException(LoginPage.class);
            } else {
                return true;
            }
        }
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
        return true;
    }
}
