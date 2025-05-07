import { useBoundStore } from '@/components/providers/StoreProvider';

export const useModal = () => {
  const isOpen = useBoundStore((state) => state.open);
  const type = useBoundStore((state) => state.type);

  const { openModal, closeModal, toggleModal } = useBoundStore(
    (state) => state.actions,
  );

  return {
    isOpen,
    type,
    openModal,
    closeModal,
    toggleModal,
  };
};
