import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import AppBar from '@material-ui/core/AppBar';

const useStyles = makeStyles({
    logo: {
        padding: 20
    }
})

const Header = () => {
    const classes = useStyles();
    return(
        <AppBar position='fixed' size='med'>
            <Typography className={classes.logo} variant='h6'>Fitness club</Typography>
        </AppBar>
    );
}

export default Header;