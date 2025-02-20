package br.com.wsp.msorder.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleName) {
        super("Not found role by: " + roleName);
    }

    public RoleNotFoundException(Long roleId) {
        super("Not found role by: " + roleId);
    }
}
