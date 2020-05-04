import React from 'react';
import PropTypes from 'prop-types';

function Email (props) {
  return (
    <div>
    {props.name}
    </div>
  );
}

Email.propTypes = {
  name: PropTypes.string.isRequired,
};

export default Email;