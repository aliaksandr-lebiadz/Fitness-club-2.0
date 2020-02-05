import React from 'react';

class Link extends React.Component{
    render(){
        const { value, url } = this.props;
        return(
            <a className="navigation_link simple" href={url}>{value}</a>
        );
    }
}

export default Link;