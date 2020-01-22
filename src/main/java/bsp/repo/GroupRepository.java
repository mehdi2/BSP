package bsp.repo;

import bsp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**Group.indexing
 * Created by Chowdhury Muhammad Mehdi Hasan on 16-Nov-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface GroupRepository extends JpaRepository<Group, BigInteger> {

//    List<Group> findByCompanyCodeAndTypeOrderByNameAsc(@Param("companyCode") int companyCode, @Param("Type") int type);
    List<Group> findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(@Param("companyCode") int companyCode, @Param("Type") int type, @Param("IsLog") boolean isLog);

//    List<Group> findByCompanyCodeAndTypeAndIdNot(@Param("companyCode") int companyCode, @Param("Type") int type, @Param("id") BigInteger id);

    List<Group> findByCompanyCodeAndTypeAndIsLogFalseAndIdNotIn(@Param("companyCode") int companyCode, @Param("Type") int type, @Param("id") List<BigInteger> id);

    //Accounts Group Controller->RemoveFunc(),
    List<Group> findByParentCodeAndIsLog(@Param("parentCode") BigInteger parentCode, @Param("IsLog") boolean isLog);

//    List<Group> findByCompanyCodeAndTrackId(@Param("companyCode") int companyCode, @Param("TrackId") BigInteger trackId);

    //For Duplicate Check
    Group findByCompanyCodeAndTypeAndNameAndIsLogFalse(@Param("companyCode") int companyCode, @Param("Type") int type,@Param("name") String name);
}
