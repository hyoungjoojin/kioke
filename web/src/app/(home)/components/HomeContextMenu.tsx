'use client';

import { ContextMenuItem } from '@/components/ui/context-menu';
import { IconName } from '@/components/ui/icon';
import { useDashboard, useDashboardActions } from '@/store/dashboard';
import { useTranslations } from 'next-intl';

export default function HomeContextMenu() {
  const t = useTranslations();

  const { isEditing } = useDashboard();
  const { setIsEditingDraft } = useDashboardActions();

  return (
    <>
      {isEditing ? (
        <ContextMenuItem
          icon={IconName.SIGN_OUT}
          onClick={() => {
            setIsEditingDraft(false);
          }}
        >
          {t('dashboard.context-menu.exit-layout')}
        </ContextMenuItem>
      ) : (
        <ContextMenuItem
          icon={IconName.EDIT}
          onClick={() => {
            setIsEditingDraft(true);
          }}
        >
          {t('dashboard.context-menu.edit-layout')}
        </ContextMenuItem>
      )}
    </>
  );
}
