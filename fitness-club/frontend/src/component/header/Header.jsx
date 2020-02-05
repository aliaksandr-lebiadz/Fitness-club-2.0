import React from 'react';
import Link from './Link';
import '../styles/Header.css';

class Header extends React.Component{
    render(){
        return(
            <div id="header">
                <div id="logo">FitnessClub</div>
                <div id="navigation">
                    <Link value="Home" url="/" />
                    <Link value="Users" url="/users" />
                </div>
            </div>
        );
    }
}

export default Header;