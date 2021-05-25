import Vue from 'vue'
import VueRouter from "vue-router"

import Main from '../components/Main'
import SymmetricAlg from '../components/SymmetricAlg'
import AsymmetricAlg from "../components/AsymmetricAlg";
import HashAlg from "../components/HashAlg";
import CpkAlg from "../components/CpkAlg";
import EcdhAlg from "../components/EcdhAlg";
import X509Parse from "../components/X509Parse";
import DataConvert from "../components/DataConvert";
import About from "../components/About";

Vue.use(VueRouter);

export const constantRoutes = [
  {
    path: '/Main',
    name: 'main',
    component: Main,
    // redirect: {name: 'SymmetricAlg'},
    children: [
      {
        path: '/SymmetricAlg',
        component: SymmetricAlg,
        name: 'SymmetricAlg',
      },
      {
        path: '/AsymmetricAlg',
        component: AsymmetricAlg,
        name: 'AsymmetricAlg',
      },
      {
        path: '/HashAlg',
        component: HashAlg,
        name: 'HashAlg',
      },
      {
        path: '/CpkAlg',
        component: CpkAlg,
        name: 'CpkAlg',
      },
      {
        path: '/EcdhAlg',
        component: EcdhAlg,
        name: 'EcdhAlg',
      },
      {
        path: '/X509Parse',
        component: X509Parse,
        name: 'X509Parse',
      },
      {
        path: '/DataConvert',
        component: DataConvert,
        name: 'DataConvert',
      },
      {
        path: '/About',
        component: About,
        name: 'About',
      }
    ]
  },
]
export default new VueRouter({
  mode: 'history', // 去掉url中的#
  routes: constantRoutes
})
