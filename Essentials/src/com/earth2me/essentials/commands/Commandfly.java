package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n._;
import com.earth2me.essentials.User;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;


public class Commandfly extends EssentialsToggleCommand
{
	public Commandfly()
	{
		super("fly", "essentials.fly.others");
	}

	@Override
	protected void run(final Server server, final CommandSender sender, final String commandLabel, final String[] args) throws Exception
	{
		otherPlayers(server, sender, args);
	}

	@Override
	protected void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception
	{
		if (args.length == 1)
		{
			Boolean toggle = matchArgument(args[0]);
			if (toggle == null && user.isAuthorized(othersPermission))
			{
				otherPlayers(server, user, args);
			}
			else
			{
				togglePlayer(user, user, toggle);
			}
		}
		else if (args.length == 2 && user.isAuthorized(othersPermission))
		{
			otherPlayers(server, user, args);
		}
		else
		{
			togglePlayer(user, user, null);
		}
	}

	@Override
	void togglePlayer(CommandSender sender, User user, Boolean enabled)
	{
		if (enabled == null)
		{
			enabled = !user.getAllowFlight();
		}

		user.setAllowFlight(enabled);

		if (!user.getAllowFlight())
		{
			user.setFlying(false);
		}

		user.sendMessage(_("flyMode", _(enabled ? "enabled" : "disabled"), user.getDisplayName()));
		if (!sender.equals(user))
		{
			sender.sendMessage(_("flyMode", _(enabled ? "enabled" : "disabled"), user.getDisplayName()));
		}
	}
}
