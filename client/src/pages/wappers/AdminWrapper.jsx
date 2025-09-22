import React, { useEffect, useState } from "react";
import { verify } from "../../api/general";
import Dashboard from "../Dashboard";
import useVerify from "../../hooks/useVerify";

const AdminWrapper = () => {
  const { data, err } = useVerify();

  return <Dashboard admin={data} />;
};

export default AdminWrapper;
