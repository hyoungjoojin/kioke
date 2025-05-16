import { useBoundStore } from '@/components/providers/StoreProvider';
import { useShallow } from 'zustand/react/shallow';

export const useModalStore = () => {
  return useBoundStore(
    useShallow((state) => {
      return {
        open: state.open,
        type: state.type,
        openModal: state.actions.openModal,
        closeModal: state.actions.closeModal,
        toggleModal: state.actions.toggleModal,
      };
    }),
  );
};
