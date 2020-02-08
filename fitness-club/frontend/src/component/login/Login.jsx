import React, { useState } from "react";
import {
  FormControl,
  TextField,
  Paper,
  Typography,
  Button,
  InputAdornment,
  IconButton
} from "@material-ui/core";
import Box from "@material-ui/core/Box";
import { makeStyles } from "@material-ui/core/styles";
import VisibilityIcon from "@material-ui/icons/Visibility";
import VisibilityOffIcon from "@material-ui/icons/VisibilityOff";
import PropTypes from "prop-types";
import { userService } from "../../service";
import { history } from "../../helpers";

const useStyles = makeStyles({
  container: {
    width: "20%",
    minHeight: 350,
    marginTop: 150,
    marginLeft: "40%"
  },
  loginTextContainer: {
    minHeight: 75
  },
  loginText: {
    fontWeight: 800,
    paddingTop: 30
  },
  input: {
    backgroundColor: "#D1D3D4",
    marginTop: 10,
    width: "80%",
    marginLeft: "10%"
  },
  loginButton: {
    width: "60%",
    marginLeft: "20%",
    marginTop: 50
  }
});

const Login = ({ setCurrentUser }) => {
  const classes = useStyles();
  const [credentials, setCredentials] = useState({ email: "", password: "" });
  const [visiblePassword, setVisiblePassword] = useState(false);

  function handleLogin() {
    console.log("kek");
    const { email, password } = credentials;
    userService.login(email, password).then(response => {
      setCurrentUser({});
      history.push("/");
    });
  }

  function handleChange(e) {
    const { name, value } = e.target;
    setCredentials(prev => ({
      ...prev,
      [name]: value
    }));
  }

  function setPasswordVisibility() {
    setVisiblePassword(!visiblePassword);
  }

  return (
    <Box className={classes.container} component={Paper}>
      <Box className={classes.loginTextContainer}>
        {" "}
        <Typography className={classes.loginText} align="center">
          LOGIN
        </Typography>
      </Box>
      <FormControl fullWidth>
        <TextField
          className={classes.input}
          required
          id="email"
          type="email"
          name="email"
          label="Email"
          value={credentials.email}
          onChange={handleChange}
          variant="outlined"
          size="small"
        />
        <TextField
          className={classes.input}
          required
          id="password"
          type={visiblePassword ? "text" : "password"}
          name="password"
          label="Password"
          value={credentials.password}
          onChange={handleChange}
          variant="outlined"
          size="small"
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton onClick={setPasswordVisibility} size="small">
                  {visiblePassword ? <VisibilityOffIcon /> : <VisibilityIcon />}
                </IconButton>
              </InputAdornment>
            )
          }}
        />
        <Button
          className={classes.loginButton}
          variant="contained"
          color="secondary"
          onClick={() => handleLogin()}
        >
          Login
        </Button>
      </FormControl>
    </Box>
  );
};

Login.propTypes = {
  setCurrentUser: PropTypes.func.isRequired
};

export default Login;
