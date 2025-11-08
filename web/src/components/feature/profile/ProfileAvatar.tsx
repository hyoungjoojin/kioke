'use client';

import { signOut } from '@/app/api/auth';
import { ModalType } from '@/components/modal/Modal';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { destroySession } from '@/lib/session';
import { useMyDashboardQuery } from '@/query/dashboard';
import { useDashboardActions } from '@/store/dashboard';
import { useModalActions } from '@/store/modal';
import { useTranslations } from 'next-intl';

export default function ProfileAvatar() {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Avatar>
          <AvatarImage src={''} />
          <AvatarFallback></AvatarFallback>
        </Avatar>
      </DropdownMenuTrigger>

      <DropdownMenuContent side='bottom' align='end'>
        <EditDashboard />
        <Settings />
        <SignOut />
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

function EditDashboard() {
  const { setIsEditingDraft, setDraft } = useDashboardActions();
  const { data: dashboard } = useMyDashboardQuery();

  if (!dashboard) {
    return null;
  }

  return (
    <DropdownMenuItem
      icon='edit'
      onClick={() => {
        setIsEditingDraft(true);
        setDraft(dashboard);
      }}
    >
      Edit Dashboard
    </DropdownMenuItem>
  );
}

function Settings() {
  const t = useTranslations();
  const { openModal } = useModalActions();

  return (
    <DropdownMenuItem
      icon='setting'
      onClick={() => {
        openModal(ModalType.SETTINGS);
      }}
    >
      {t('header.profile.settings')}
    </DropdownMenuItem>
  );
}

function SignOut() {
  const t = useTranslations();

  const signOutButtonClickHandler = async () => {
    await signOut();
    destroySession();
  };

  return (
    <DropdownMenuItem icon='logout' onClick={signOutButtonClickHandler}>
      {t('header.profile.sign-out')}
    </DropdownMenuItem>
  );
}
