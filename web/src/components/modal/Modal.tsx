'use client';

import { Button } from '../ui/button';
import { IconName } from '../ui/icon';
import { useClickOutside } from '@/hooks/useClickOutside';
import { cn } from '@/lib/utils';
import { useModal, useModalActions } from '@/store/modal';
import { AnimatePresence, motion } from 'motion/react';
import { useTranslations } from 'next-intl';
import dynamic from 'next/dynamic';
import type { ComponentType} from 'react';
import { useRef } from 'react';

export const enum ModalType {
  SETTINGS,
}

const modals: {
  [K in ModalType]: {
    value: string;
    content: ComponentType<{}>;
  };
} = {
  [ModalType.SETTINGS]: {
    value: 'settings',
    content: dynamic(() => import('./settings/SettingsModal')),
  },
};

export default function Modal() {
  const t = useTranslations();

  const { type, open } = useModal();
  const { closeModal } = useModalActions();

  const modalRef = useRef<HTMLDivElement | null>(null);
  useClickOutside(modalRef, () => {
    closeModal();
  });

  if (type === null) {
    return null;
  }

  const modal = modals[type];

  return (
    <AnimatePresence>
      {open && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{
            duration: 0.1,
          }}
          className={cn(
            'fixed top-0 left-0 h-dvh w-dvw flex items-center justify-center',
            'bg-[rgb(0,0,0,0.5)]',
            'z-50',
          )}
        >
          <div
            className={cn(
              'flex flex-col',
              'bg-background shadow-lg',
              'w-4/5 h-4/5',
              'rounded-2xl',
              'z-[999] pl-7 pr-5 py-3',
            )}
            onClick={(e) => {
              e.stopPropagation();
            }}
            ref={modalRef}
          >
            <div className='flex justify-between items-center h-12'>
              <h1 className='text-xl'>{t(`modal.${modal.value}.title`)}</h1>

              <Button
                variant='ghost'
                icon={IconName.X}
                onClick={() => {
                  closeModal();
                }}
              />
            </div>

            <div className='grow'>
              <modal.content />
            </div>
            <div className='absolute top-3 right-3'></div>
          </div>
        </motion.div>
      )}
    </AnimatePresence>
  );
}
