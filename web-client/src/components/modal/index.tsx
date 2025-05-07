'use client';

import { ModalType } from '@/constants/modal';
import { useModal } from '@/hooks/store/modal';
import { cn } from '@/lib/utils';
import dynamic from 'next/dynamic';

const SettingsModal = dynamic(() => import('./SettingsModal'));

function getModal(type: ModalType) {
  switch (type) {
    case ModalType.SETTINGS:
      return <SettingsModal />;

    default:
      return null;
  }
}

export default function Modal() {
  const { type, isOpen, closeModal } = useModal();

  if (!isOpen) {
    return null;
  }

  const modal = getModal(type);
  if (modal === null) {
    return null;
  }

  return (
    <div
      className={cn(
        'fixed top-0 left-0 w-full h-full',
        'bg-[rgb(0,0,0,0.5)]',
        'flex justify-center align-center',
        'z-50',
      )}
      onClick={() => {
        closeModal();
      }}
    >
      <div
        className={cn(
          'absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2',
          'bg-white shadow-lg',
          'w-4/5 h-4/5',
          'rounded-lg',
        )}
        onClick={(e) => {
          e.stopPropagation();
        }}
      >
        {modal}
      </div>
    </div>
  );
}
