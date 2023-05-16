import "./App.css";
import Axios from "axios";
import { useState, useEffect, useCallback } from "react";
import { useDropzone } from "react-dropzone";
function UserProfile() {
  const [userProfiles, setUserProfiles] = useState([]);
  const userFetchUserProfiles = () => {
    Axios.get("http://localhost:8080/api/v1/user-profile")
      .then((result) => {
        setUserProfiles(result.data);
      })
      .catch((err) => console.log(err));
  };
  useEffect(() => {
    userFetchUserProfiles();
  }, []);
  return (
    <div>
      {userProfiles.map((userProfile) => (
        <div key={userProfile.userProfileID}>
          <br />
          <img style={{height:"250px",width:"350px"}} src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileID}/image/download`}/>
          <h2>{userProfile.userName}</h2>
          <h2>{userProfile.userProfileID}</h2>
          <MyDropzone {...userProfile}/>
          <br />
        </div>
      ))}
    </div>
  );
}
function MyDropzone({userProfileID}) {
  console.log(userProfileID);
  const onDrop = useCallback((acceptedFiles) => {
    const file = acceptedFiles[0];
    const formData = new FormData();
    formData.append("file", file)
    Axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileID}/image/upload`,formData,{headers:{
      "Content-Type":"multipart/form-data"
    }}).then(()=>console.log("file uploaded sucessfully")).catch(err=>console.log(err))
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <div style={{border:"solid #3182CE 2px", padding:"10px", backgroundColor:"#90CDF4",cursor:"pointer"}} {...getRootProps()}>
      <input {...getInputProps()} />
      {isDragActive ? (
        <p>Drop the profile image here ...</p>
      ) : (
        <p>Drag 'n' drop some profile image here, or click to select profile image</p>
      )}
    </div>
  );
}
function App() {
  return (
    <>
      <UserProfile />
    </>
  );
}

export default App;
