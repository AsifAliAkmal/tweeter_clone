package server.server.service.tweets;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.model.Follower;
import server.server.repository.FollowerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowerService {
    @Autowired
    FollowerRepository repository;

    public void saveToDB(Follower follower){
        repository.save(follower);
    }

    public Follower findByUserId(Long userId){
        return repository.findByUserId(userId);
    }

    public void addFollowing(Long userId, Long followingId) {
        Follower follower = repository.findByUserId(userId);

        if(follower == null){
            follower = new Follower();
            follower.setUserId(userId);
        }

        String following = follower.getFollowing();
        if(following == null || following.isEmpty()){
            following = followingId.toString();
        }else{
            following +=","+followingId.toString();
        }
        follower.setFollowing(following);
        repository.save(follower);
    }

    public void removeFollowing(Long userId,Long followingId){
        Follower follower = repository.findByUserId(userId);
        if(follower == null){
            //todo throw Exception
            return;
        }
        String[] arr = follower.getFollowing().split(",");
        List<String>  idList = new ArrayList<>();
        for(int i = 0 ; i < arr.length ; i++){
            if(Long.parseLong(arr[i]) == followingId){
                continue;
            }
            idList.add(arr[i]);
        }
        follower.setFollowing(String.join(",",idList));
        repository.save(follower);
    }
}
