import type { DashboardSlice} from './dashboard';
import { createDashboardSlice } from './dashboard';
import type { ModalSlice} from './modal';
import { createModalSlice } from './modal';
import { create } from 'zustand';

export type Store = ModalSlice & DashboardSlice;

export const createStore = () => {
  return create<Store>((...a) => ({
    ...createModalSlice(...a),
    ...createDashboardSlice(...a),
  }));
};
