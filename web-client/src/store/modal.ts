import { ModalType } from '@/constants/modal';
import { produce } from 'immer';
import { createStore } from 'zustand';

type ModalState = {
  open: boolean;
  type: ModalType;
};

type ModalActions = {
  openModal: (type: ModalType) => void;
  closeModal: () => void;
  toggleModal: () => void;
};

type ModalStore = ModalState & {
  actions: ModalActions;
};

const initialState: ModalState = {
  open: false,
  type: ModalType.NULL,
};

const createModalStore = () => {
  return createStore<ModalStore>((set) => ({
    ...initialState,
    actions: {
      openModal: (type: ModalType) => {
        set(
          produce((store: ModalStore) => {
            store.open = true;
            store.type = type;
          }),
        );
      },
      closeModal: () => {
        set(
          produce((store: ModalStore) => {
            store.open = false;
          }),
        );
      },
      toggleModal: () => {
        set(
          produce((store: ModalStore) => {
            store.open = !store.open;
          }),
        );
      },
    },
  }));
};

export { type ModalStore, createModalStore };
