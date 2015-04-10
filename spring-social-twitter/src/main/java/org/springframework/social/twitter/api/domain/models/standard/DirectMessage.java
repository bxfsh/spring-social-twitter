/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.domain.models.standard;

import java.util.Date;

import org.springframework.social.twitter.api.impl.standard.models.TwitterObject;
import org.springframework.social.twitter.api.impl.standard.models.TwitterProfile;

/**
 * Represents a direct message.
 * @author Craig Walls
 */
public class DirectMessage extends TwitterObject {
	private final long id;
	private final String text;
	private final TwitterProfile sender;
	private final TwitterProfile recipient;
	private final Date createdAt;

	public DirectMessage(long id, String text, TwitterProfile sender, TwitterProfile recipient, Date createdAt) {
		this.id = id;
		this.text = text;
		this.sender = sender;
		this.recipient = recipient;
		this.createdAt = createdAt;
	}
	
	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public TwitterProfile getSender() {
		return sender;
	}
	
	public TwitterProfile getRecipient() {
		return recipient;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

}