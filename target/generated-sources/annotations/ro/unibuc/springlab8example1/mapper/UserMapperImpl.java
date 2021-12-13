package ro.unibuc.springlab8example1.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.User.UserBuilder;
import ro.unibuc.springlab8example1.domain.UserDetails;
import ro.unibuc.springlab8example1.domain.UserDetails.UserDetailsBuilder;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.dto.UserDto.UserDtoBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-13T20:55:49+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto mapToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.cnp( userUserDetailsCnp( user ) );
        userDto.age( userUserDetailsAge( user ) );
        userDto.otherInformation( userUserDetailsOtherInformation( user ) );
        userDto.id( user.getId() );
        userDto.username( user.getUsername() );
        userDto.fullName( user.getFullName() );

        return userDto.build();
    }

    @Override
    public User mapToEntity(UserDto user) {
        if ( user == null ) {
            return null;
        }

        UserBuilder user1 = User.builder();

        user1.userDetails( userDtoToUserDetails( user ) );
        user1.id( user.getId() );
        user1.username( user.getUsername() );
        user1.fullName( user.getFullName() );

        return user1.build();
    }

    private String userUserDetailsCnp(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String cnp = userDetails.getCnp();
        if ( cnp == null ) {
            return null;
        }
        return cnp;
    }

    private Integer userUserDetailsAge(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        Integer age = userDetails.getAge();
        if ( age == null ) {
            return null;
        }
        return age;
    }

    private String userUserDetailsOtherInformation(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String otherInformation = userDetails.getOtherInformation();
        if ( otherInformation == null ) {
            return null;
        }
        return otherInformation;
    }

    protected UserDetails userDtoToUserDetails(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserDetailsBuilder userDetails = UserDetails.builder();

        userDetails.cnp( userDto.getCnp() );
        userDetails.age( userDto.getAge() );
        userDetails.otherInformation( userDto.getOtherInformation() );

        return userDetails.build();
    }
}
