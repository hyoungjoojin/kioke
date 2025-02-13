import { create } from "zustand";
import { createShelfSlice, type ShelfSlice } from "./shelf";

export type Store = ShelfSlice;

const createBoundStore = () => {
  return create<Store>((...a) => ({
    ...createShelfSlice(...a),
  }));
};

export default createBoundStore;
