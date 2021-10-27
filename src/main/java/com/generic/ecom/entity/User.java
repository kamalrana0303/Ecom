package com.generic.ecom.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name="user")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="username",unique = true)
    private String username;
    @Column(name="password",unique = true)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns ={ @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false,updatable = false) },
            inverseJoinColumns ={ @JoinColumn(name="role_id", referencedColumnName = "id",nullable = false,updatable = false)}
    )
    @Column(name="role_id")
    private Set<Role> roles;

    private Date creationDate;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public User(String username, String password, Collection<Role> roles) {
        this(username, password, true, true, true, true, roles);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked,
                Collection<Role> roles) {
        Assert.isTrue(username != null && !"".equals(username) && password != null,
                "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.roles = Collections.unmodifiableSet(sortAuthorities(roles));
        this.creationDate= new Date();
    }

    private static SortedSet<Role> sortAuthorities(Collection<Role> roles) {
        Assert.notNull(roles, "Cannot pass a null Role collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<Role> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
        for (Role role : roles) {
            Assert.notNull(role, "Roles list cannot contain any null elements");
            sortedAuthorities.add(role);
        }
        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<Role>, Serializable {

        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(Role role1, Role role2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set. If the authority is null, it is a custom authority and should
            // precede others.
            if (role1.getName() == null) {
                return -1;
            }
            if (role2.getName() == null) {
                return 1;
            }
            return role1.getName().compareTo(role2.getName());
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role-> {
            return new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role.getName();
                }
            };
        }).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
