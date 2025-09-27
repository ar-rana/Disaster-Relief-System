import React from "react";
import useVerify from "../../hooks/useVerify";
import Navigation from "../Navigation";
import loading_eclipse from "../../assets/loading_eclipse.svg";

const NavigationWrapper = () => {
  const { data, err } = useVerify();

  if (data) {
    return <Navigation />;
  } else {
    return (
      <img
        className="max-w-12 absolute top-1/2 left-1/2 transform translate-x-1/2 translate-y-1/2"
        src={loading_eclipse}
        alt="Loading..."
      />
    );
  }
};

export default NavigationWrapper;
